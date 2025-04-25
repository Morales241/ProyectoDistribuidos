import React from 'react';
import { createRoot } from 'react-dom/client';
import Mercado from './pages/Mercado';

const root = createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <Mercado />
  </React.StrictMode>
);

