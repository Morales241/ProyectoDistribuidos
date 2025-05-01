import React from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Consumidor from "./Consumidor";
import Login from "./Login";
import Register from "./Register";

const root = createRoot(document.getElementById("root"));

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/consumidor" element={<Consumidor />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);
