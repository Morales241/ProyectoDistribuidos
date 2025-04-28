import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [correo, setCorreo] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate(); 

  const handleLogin = async () => {
    console.log('Iniciando sesión con:', correo, password);
    const login = async () => {
        const response = await axios.post('http://localhost:8080/comercio/login', null, {
          params: {
            correo: correo,
            password: password
          }
        });
      
        if (response.status === 200) {
          const comercio = response.data;
          localStorage.setItem('comercioId', comercio.id);
          localStorage.setItem('comercioNombre', comercio.nombre);
          navigate('/mercado'); 
        } else {
          alert('Credenciales incorrectas');
        }
      };
  };

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
    </div>
  );
};

export default Login;
