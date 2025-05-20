import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css";
import bcrypt from "bcryptjs";
import axios from "axios";

const Login = () => {
    const [correo, setCorreo] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = async () => {
        const response = await axios.post("http://localhost:8766/DOMINIOCONSUMIDOR/consumidores/inicioSesion", null, {
            params: {
                correo: correo,
                contrasena: password
            }
        });

        if (response.status === 200) {
            const consumidor = response.data;
            localStorage.setItem("consumidorId", consumidor.id);
            localStorage.setItem("consumidorNombre", consumidor.nombre);

            console.info("id Consumidor: ", consumidor.id);
            obtenerToken();
        } else {
            alert("Credenciales incorrectas");
        }
    };

    const obtenerToken = async () => {
    const response = await axios.post('http://localhost:8766/GENERADORJWT/auth/generarToken', null, {
      params: {
        tipoUsuario: "consumidor",
        correo: correo,
        contrasena: password
      }
    });
    if (response.status === 200) {
      const token = response.data;
      localStorage.setItem('token', token);
      console.info('token', token);
      navigate("/consumidor");
    } else {
      alert('No se autentico la sesión');
    }
  }
    
    const irARegistro = () => {
        navigate("/register");
    };

    return (
        <div className="login-container">
            <h2>Iniciar Sesión</h2>
            <input type="email" placeHolder="Correo" value={correo} onChange={(e) => setCorreo(e.target.value)} />
            <input
                type="password"
                placeholder="Contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button className="login-button" onClick={handleLogin}>
                Entrar
            </button>
            <button className="register-button" onClick={irARegistro}>
                Registrarse
            </button>
        </div>
    );
};

export default Login;
