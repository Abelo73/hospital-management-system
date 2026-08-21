# 💬 Hospital Management System (HMS) — Real-Time Chat & Support Architecture (`CHAT_SUPPORT_MODULE.md`)

## 📋 1. Executive Overview

The Real-Time Chat & Support Module provides instant communication across hospital departments, secure Doctor-Patient teleconsultations, emergency broadcast notifications, and live patient support in the Hospital Management System (HMS).

This document details the Spring WebSockets + STOMP messaging architecture, Redis Pub/Sub message broker integration, chat channel models, file attachment security, and UI integration.

---

## 🏗️ 2. WebSockets Architecture & Infrastructure

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           HMS FRONTEND (hms-ui)                         │
│                    (STOMP Client / SockJS WebSocket)                    │
└───────┬─────────────────────────┬─────────────────────────┬─────────────┘
        │ /topic/emergency        │ /queue/messages         │ /topic/telehealth
┌───────▼─────────────────────────▼─────────────────────────▼─────────────┐
│                 Spring WebSockets + STOMP Gateway                       │
│                     (Endpoint: /api/v1/ws-hms)                          │
├─────────────────────────────────────────────────────────────────────────┤
│    JWT Interceptor   │   Channel Interceptor   │   Message Mapper   │
└───────┬───────────────────────────────────────────────────┬─────────────┘
        │                                                   │
┌───────▼────────────────┐                         ┌───────▼─────────────┐
│ Redis Pub/Sub Broker   │                         │ PostgreSQL          │
│ (Scalable Multi-Node)  │                         │ (Persistent History)│
└────────────────────────┘                         └─────────────────────┘
```

---

## 💬 3. Communication Channels & Workflows

### 3.1 👥 Internal Staff Collaboration Chat
* **Direct Messaging**: Encrypted 1-on-1 messaging between Doctors, Nurses, Pharmacists, and Lab Techs.
* **Departmental Channels**: Public and private channels (e.g. `#emergency-ward`, `#pharmacy-orders`, `#lab-alerts`).
* **Clinical Case Consultation**: Doctors can share patient clinical snippets for multi-disciplinary team (MDT) discussion.

### 3.2 🚨 Emergency Broadcast System (Code Alerts)
* **Code Blue / Rapid Response Alerts**: High-priority instant alert channel broadcasting sound/visual notifications to all active nurses and doctors on shift.
* **Acknowledge Button**: Staff click to log immediate response availability.

### 3.3 👨‍⚕️ Doctor-Patient Teleconsultation Chat
* **Session-Bound Chat**: Attached directly to active virtual consultation sessions (`/doctors/consultations/{id}`).
* **Secure Attachment Sharing**: Share PDF lab results, prescription receipts, or wound photo attachments stored securely in MinIO.

### 3.4 🎧 Patient Support Helpdesk
* **Front Desk Live Support**: Patients in the self-service portal can chat with Receptionists for appointment booking queries or directions.
* **Auto-Routing**: Inbound patient support chats auto-route to available Receptionist workstations.

---

## 🔒 4. Chat Security & Compliance

1. **JWT Handshake Authentication**: WebSockets require valid JWT access token validation during initial HTTP upgrade handshake.
2. **Channel Authorization**: Interceptors verify user roles before allowing subscription to restricted channels (e.g., `#admin-board` requires `ADMIN` role).
3. **Encrypted Message History**: Messages stored in PostgreSQL database are encrypted at rest.

---

## 🌐 5. Backend Spring WebSockets Reference

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-hms")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
```
