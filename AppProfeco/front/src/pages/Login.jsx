import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';
// import bcrypt from 'bcryptjs';
import axios from "axios";

const Login = () => {
  const [correo, setCorreo] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {


    if (response.status === 200) {
      const comercio = response.data;
      localStorage.setItem('comercioId', comercio.id);
      console.info('id Comercio:', comercio.id);
      navigate('/mercado');
    } else {
      alert('Credenciales incorrectas');
    }

  };

  /*) const encriptarContraseña = async (password) => {
    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(password, salt);
    return hashedPassword;
  };*/


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
