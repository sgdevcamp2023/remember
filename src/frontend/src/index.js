import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import { MediaStreamProvider } from "./contexts/MediaStreamContext";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <BrowserRouter>
    <MediaStreamProvider>
      <App />
    </MediaStreamProvider>
  </BrowserRouter>
);
