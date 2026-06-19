import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";

// Apply dark mode from localStorage on first load
const stored = localStorage.getItem("ui-storage");
if (stored) {
  const parsed = JSON.parse(stored);
  if (parsed?.state?.darkMode) {
    document.documentElement.classList.add("dark");
  }
}

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);