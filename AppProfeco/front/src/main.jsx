import React from 'react';
import ReactDOM from 'react-dom/client';
import Profeco from './pages/Profeco.jsx';
import './pages/Profeco.css'; 

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Profeco onVolver={() => {}} />
  </React.StrictMode>
);
