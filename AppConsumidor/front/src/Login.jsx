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
        const response = await axios.post("http://localhost:8082/consumidores/inicioSesion", null, {
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
            navigate("/consumidor");
        } else {
            alert("Credenciales incorrectas");
        }
    };

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
