import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';
import axios from "axios";

const Login = () => {
  const [correo, setCorreo] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {

    const response = await axios.post('http://localhost:8766/DOMINIOCOMERCIO/comercios/inicioSesion', null, {
      params: {
        correo: correo,
        contrasena: password
      }
    });

    if (response.status === 200) {
      const comercio = response.data;
      localStorage.setItem('nombreComercio', comercio.nombre);
      localStorage.setItem('comercioId', comercio.id);
      console.info('nombre Comercio:', comercio.nombre);
      console.info('comercioId:', comercio.id);
      obtenerToken();
    } else {
      alert('Credenciales incorrectas');
    }

  };
  
  const obtenerToken = async () => {
    const response = await axios.post('http://localhost:8766/GENERADORJWT/auth/generarToken', 
      {
        tipoUsuario: "comercio",
        correo: correo,
        contrasena: password
      }
    );
    if (response.status === 200) {
      const token = response.data;
      localStorage.setItem('token', token);
      console.info('token', token);
      navigate('/mercado');
    } else {
      alert('No se autentico la sesión');
    }
  }

  const irARegistro = () => {
    navigate('/register');
  };

  return (
    <div className="login-container">
      <h2>Iniciar Sesión</h2>
      <input
        type="email"
        placeholder="Correo"
        value={correo}
        onChange={(e) => setCorreo(e.target.value)}
      />
      <input
        type="password"
        placeholder="Contraseña"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button className="login-button" onClick={handleLogin}>Entrar</button>

      <button className="register-button" onClick={irARegistro}>Registrarse</button>
      <button className="boton-recuperar">¿Olvidaste tu contraseña?</button>

    </div>
  );
};

export default Login;
