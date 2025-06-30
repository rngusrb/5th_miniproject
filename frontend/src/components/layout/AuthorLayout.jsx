import React from "react";
import HeaderBar from "./HeaderBar";

export default function AuthorLayout({ children }) {
  return (
    <div>
      <HeaderBar />
      <div style={{
        maxWidth: "1200px",
        margin: "0 auto",
        padding: "2rem 1rem"
      }}>
        {children}
      </div>
    </div>
  );
}
