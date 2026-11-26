import { useState } from "react";

import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";

import "./App.css";

function App() {

    const [isLoggedIn, setIsLoggedIn] = useState(
        Boolean(
            localStorage.getItem("jwtToken")
        )
    );

    const [showRegister, setShowRegister] =
        useState(false);

    const handleLogin = () => {

        setIsLoggedIn(true);
    };

    const handleLogout = () => {

        localStorage.removeItem(
            "jwtToken"
        );

        setIsLoggedIn(false);
    };

    const handleRegister = () => {

        setShowRegister(true);
    };

    const handleRegisterSuccess = () => {

        setShowRegister(false);
    };

    const handleBackToLogin = () => {

        setShowRegister(false);
    };

    if (!isLoggedIn) {

        if (showRegister) {

            return (
                <Register
                    onRegisterSuccess={
                        handleRegisterSuccess
                    }
                    onBackToLogin={
                        handleBackToLogin
                    }
                />
            );
        }

        return (
            <Login
                onLogin={handleLogin}
                onRegister={handleRegister}
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
