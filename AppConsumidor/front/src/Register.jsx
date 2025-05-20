import React, { useState } from "react";
import "./Register.css";
import bcrypt from "bcryptjs";
import axios from "axios";

const Register = () => {
    const [nombre, setNombre] = useState("");
    const [correo, setCorreo] = useState("");
    const [password, setPassword] = useState("");

    const handleRegister = async () => {
        try {
            const contraEncriptada = await encriptarContrasena(password);

            const consumidorData = {
                id: null,
                nombre: nombre,
                correo: correo,
                fechaRegistro: new Date(),
                contrasena: contraEncriptada
            };

            const response = await axios.post("http://localhost:8766/DOMINIOCONSUMIDOR/consumidores/registrar", consumidorData, {
                headers: { "Content-Type": "application/json" }
            });
            console.log("Registro exitoso", response.data);
            alert("Registro exitoso");
            const consumidor = response.data;
            const responseCarrito = await axios.post(`http://localhost:8766/DOMINIOCONSUMIDOR/carritos/CrerCarrito/${consumidor.id}`);

            window.history.back();
        } catch (error) {
            console.error("Error al registrar:", error);
            alert("Error al registrar");
        }
    };

    const encriptarContrasena = async (password) => {
        const salt = await bcrypt.genSalt(10);
        const hashedPassword = await bcrypt.hash(password, salt);
        return hashedPassword;
    };

    const irALogin = () => {
        window.history.back();
    };

    return (
        <div className="register-container">
            <h2>Registrarse</h2>
            <input type="text" placeholder="Nombre" value={nombre} onChange={(e) => setNombre(e.target.value)} />
            <input type="email" placeholder="Correo" value={correo} onChange={(e) => setCorreo(e.target.value)} />
            <input
                type="password"
                placeholder="Contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button className="register-button" onClick={handleRegister}>
                Registrar
            </button>
            <button className="login-button" onClick={irALogin}>
                Iniciar Sesión
            </button>
        </div>
    );
};

export default Register;
