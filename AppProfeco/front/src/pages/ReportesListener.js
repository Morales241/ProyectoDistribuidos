// ReportesListener.js
import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const ReportesListener = () => {
  const [mensaje, setMensaje] = useState(null);

  useEffect(() => {
    // Crear el cliente STOMP
    const socket = new SockJS('http://localhost:8080/ws'); // Asegúrate que coincida con tu backend
    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('Conectado a WebSocket');

        // Suscribirse al canal de reportes
        stompClient.subscribe('/topic/reportes', (message) => {
          setMensaje(message.body);
        });
      },
    });

    stompClient.activate();

    return () => {
      stompClient.deactivate();
    };
  }, []);

  return (
    <div>
      {mensaje && (
        <div style={{
          padding: '1rem',
          margin: '1rem',
          backgroundColor: '#e0ffe0',
          border: '1px solid #00aa00',
          borderRadius: '8px'
        }}>
          <strong>Mensaje recibido:</strong> {mensaje}
        </div>
      )}
    </div>
  );
};

export default ReportesListener;
