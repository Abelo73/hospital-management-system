# JVM Crash Log Analysis

## Overview
Multiple JVM crash logs (`hs_err_pid*.log`) were found in the project directory, indicating stability issues with the current JVM configuration.

## Crash Log Summary

### Total Crash Logs Found
- 6 crash log files: `hs_err_pid27753.log`, `hs_err_pid37718.log`, `hs_err_pid41847.log`, `hs_err_pid64178.log`, `hs_err_pid76037.log`, `hs_err_pid81372.log`

### Common Characteristics

**JVM Version:**
- OpenJDK Runtime Environment (21.0.11+10)
- OpenJDK 64-Bit Server VM (21.0.11+10-1-24.04.2-Ubuntu)
- G1 Garbage Collector
- Ubuntu 24.04.4 LTS

**System:**
- Intel(R) Core(TM) i7-8650U CPU @ 1.90GHz
- 8 cores
- 31GB RAM

## Crash Analysis

### Crash 1: hs_err_pid81372.log
**Error:** SIGSEGV (0xb) - Segmentation fault
**Location:** `G1ParScanThreadState::trim_queue_to_threshold(unsigned int)+0x1b29`
**Thread:** GC Thread#0
**Time:** 3.873185 seconds after startup
**Command:** Maven spring-boot:run

**Heap Status:**
- Total: 516096K
- Used: 322531K
- Max Capacity: 7992MB
- GC: G1 Garbage Collector

**Problem:** Crash during parallel garbage collection in G1ParScanThreadState

### Crash 2: hs_err_pid76037.log
**Error:** SIGSEGV (0xb) - Segmentation fault
**Location:** `OopOopIterateDispatch<G1CMOopClosure>::Table::oop_oop_iterate<InstanceKlass, narrowOop>`
**Thread:** G1 Conc#1 (Concurrent GC thread)
**Time:** 8.953991 seconds after startup
**Command:** Direct application run with `-XX:TieredStopAtLevel=1`

**Problem:** Crash during concurrent marking phase of G1 GC

## Root Cause Analysis

### Primary Issue
The crashes are occurring in the G1 Garbage Collector internals, specifically:
1. Parallel evacuation phase (G1ParScanThreadState)
2. Concurrent marking phase (G1CMOopClosure)

### Likely Causes

1. **JVM Bug in OpenJDK 21.0.11**
   - OpenJDK 21 is relatively new and may have stability issues
   - The crashes are in JVM internal code (libjvm.so)
   - Multiple different crash locations suggest a broader JVM issue

2. **Memory Corruption**
   - SIGSEGV with invalid memory access
   - Could be caused by native code interactions
   - Possible heap corruption

3. **G1 GC Configuration Issues**
   - G1 GC may not be stable with the current heap configuration
   - Large heap (7992MB) with G1 may have edge cases

4. **Ubuntu 24.04 Compatibility**
   - Ubuntu 24.04 is very new (released April 2024)
   - OpenJDK 21.0.11 may have compatibility issues with this OS version

## Recommendations

### Immediate Actions (High Priority)

1. **Downgrade JVM Version**
   ```bash
   # Switch to a more stable JVM version
   # Recommended: OpenJDK 17 LTS or OpenJDK 21.0.2 (LTS)
   sudo apt install openjdk-17-jdk
   # OR
   sudo apt install openjdk-21-jdk=21.0.2+13-1ubuntu1
   ```

2. **Change Garbage Collector**
   ```yaml
   # In application.yaml, add JVM options to use different GC
   # Add to MAVEN_OPTS or JVM_ARGS:
   # -XX:+UseG1GC -> -XX:+UseZGC (Java 21+) or -XX:+UseParallelGC
   ```

3. **Update pom.xml to specify JVM version**
   ```xml
   <properties>
       <java.version>17</java.version>
       <maven.compiler.source>17</maven.compiler.source>
       <maven.compiler.target>17</maven.compiler.target>
   </properties>
   ```

### Configuration Changes

4. **Add JVM Memory Options**
   ```yaml
   # In application.yaml or as environment variables
   JAVA_OPTS: >
     -Xms512m
     -Xmx2g
     -XX:+UseG1GC
     -XX:MaxGCPauseMillis=200
     -XX:+HeapDumpOnOutOfMemoryError
     -XX:HeapDumpPath=/tmp/heapdump.hprof
   ```

5. **Disable Tiered Compilation (if using Java 21)**
   ```bash
   # The crash log shows -XX:TieredStopAtLevel=1 was used
   # Try without this flag or with different value
   ```

### System-Level Fixes

6. **Update System Packages**
   ```bash
   sudo apt update
   sudo apt upgrade
   sudo apt install --reinstall openjdk-21-jdk
   ```

7. **Check for Memory Issues**
   ```bash
   # Check system memory
   free -h
   
   # Check for memory errors
   sudo memtest86+  # Requires reboot
   ```

### Alternative Solutions

8. **Use Docker with Stable JVM**
   ```dockerfile
   FROM eclipse-temurin:17-jdk-jammy
   # This uses a stable Ubuntu 22.04 with Java 17
   ```

9. **Use SDKMAN for JVM Management**
   ```bash
   # Install SDKMAN
   curl -s "https://get.sdkman.io" | bash
   
   # Install stable Java version
   sdk install java 17.0.11-tem
   sdk use java 17.0.11-tem
   ```

## Testing Steps

### After Making Changes

1. **Clean and Rebuild**
   ```bash
   ./mvnw clean
   ./mvnw spring-boot:run
   ```

2. **Monitor for Crashes**
   - Run application for extended period
   - Check for new hs_err_pid*.log files
   - Monitor application logs for GC issues

3. **Enable GC Logging**
   ```bash
   JAVA_OPTS="-Xlog:gc*:file=gc.log:time,tags:filecount=5,filesize=10m"
   ```

4. **Load Test**
   ```bash
   # Use Apache Bench or similar to test under load
   ab -n 1000 -c 10 http://localhost:8080/api/v1/actuator/health
   ```

## Prevention

### Monitoring

1. **Add JVM Monitoring**
   - Enable JMX monitoring
   - Use tools like VisualVM, JConsole
   - Consider APM tools (New Relic, Datadog)

2. **Add Health Checks**
   - Implement custom health indicators
   - Monitor memory usage
   - Alert on high GC pause times

3. **Add Crash Detection**
   - Script to detect hs_err_pid*.log files
   - Send alerts on JVM crashes
   - Auto-restart with monitoring

## Cleanup

### Remove Old Crash Logs
```bash
# After fixing the issue, remove old crash logs
rm hs_err_pid*.log
rm core.*  # If core dumps exist
```

### Add to .gitignore
```gitignore
# JVM crash logs
hs_err_pid*.log
core.*
heapdump.hprof
```

## Conclusion

The JVM crashes are caused by instability in OpenJDK 21.0.11 on Ubuntu 24.04, specifically in the G1 Garbage Collector. The recommended solution is to downgrade to a stable JVM version (OpenJDK 17 LTS) or use a different garbage collector.

**Priority:** HIGH - These crashes prevent stable operation of the application.

**Estimated Fix Time:** 30 minutes to 1 hour (including testing)

**Risk:** LOW - Downgrading JVM is a safe operation with minimal risk to application functionality.
