import { useState } from "react";

import Login from "./components/Login";
import Dashboard from "./components/Dashboard";

import "./App.css";

function App() {

    const [isLoggedIn, setIsLoggedIn] = useState(
        Boolean(
            localStorage.getItem("jwtToken")
        )
    );

    const handleLogin = () => {

        setIsLoggedIn(true);
    };

    const handleLogout = () => {

        localStorage.removeItem(
            "jwtToken"
        );

        setIsLoggedIn(false);
    };

    if (!isLoggedIn) {

        return (
            <Login
                onLogin={handleLogin}
            />
        );
    }

    return (
        <Dashboard
            onLogout={handleLogout}
        />
    );
}

export default App;