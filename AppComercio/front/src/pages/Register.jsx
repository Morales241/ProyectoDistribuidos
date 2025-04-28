import React, { useState } from 'react';
import './Register.css';
import bcrypt from 'bcryptjs';

const Register = () => {
    const [nombre, setNombre] = useState('');
    const [correo, setCorreo] = useState('');
    const [password, setPassword] = useState('');
    const [tipoComercio, setTipoComercio] = useState('');

    const handleRegister = async () => {
        try {
            const contraEncriptada = await encriptarContraseña(password);
    
            const comercioData = {
                nombre: nombre,
                correo: correo,
                contraseña: contraEncriptada, 
                tipo: tipoComercio
            };
    
            const response = await axios.post('http://localhost:8080/comercios/guardar', comercioData);
            
            console.log('Registro exitoso', response.data);
            alert('Registro exitoso');
        } catch (error) {
            console.error('Error al registrar:', error);
            alert('Error al registrar');
        }
    };

    const encriptarContraseña = async (password) => {
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
            <input
                type="text"
                placeholder="Nombre del Comercio"
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
            />
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

            <select
                className="select-comercio"
                value={tipoComercio}
                onChange={(e) => setTipoComercio(e.target.value)}
            >
                <option value="">Selecciona el tipo de comercio</option>
                <option value="Restaurante">SuperMercado</option>
                <option value="Ambulante">Ambulante</option>
                <option value="Mercado Local">Mercado Local</option>
            </select>

            <button className="register-button" onClick={handleRegister}>Registrar</button>
            <button className="login-button" onClick={irALogin}>Iniciar Sesión</button>
        </div>
    );
};

export default Register;
