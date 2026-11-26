import { useState } from "react";
import { loginUser } from "../services/api";

function Login({ onLogin }) {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {

        event.preventDefault();

        setError("");

        if (!email || !password) {
            setError("Email and password are required");
            return;
        }

        try {

            setLoading(true);

            const data = await loginUser(
                email,
                password
            );

            localStorage.setItem(
                "jwtToken",
                data.token
            );

            onLogin();

        } catch (error) {

            setError(error.message);

        } finally {

            setLoading(false);
        }
    };

    return (
        <div className="login-page">

            <div className="login-container">

                <div className="login-branding">

                    <div className="brand-content">

                        <h1>Sensor Monitor</h1>

                        <p>
                            Live Sensor Monitoring Dashboard
                        </p>

                    </div>

                </div>

                <div className="login-form-section">

                    <div className="login-form-container">

                        <h2>Welcome back</h2>

                        <p className="login-subtitle">
                            Login to monitor your sensors
                        </p>

                        <form onSubmit={handleSubmit}>

                            <div className="form-group">

                                <label htmlFor="email">
                                    Email
                                </label>

                                <input
                                    id="email"
                                    type="email"
                                    placeholder="Enter your email"
                                    value={email}
                                    onChange={(event) =>
                                        setEmail(event.target.value)
                                    }
                                />

                            </div>

                            <div className="form-group">

                                <label htmlFor="password">
                                    Password
                                </label>

                                <input
                                    id="password"
                                    type="password"
                                    placeholder="Enter your password"
                                    value={password}
                                    onChange={(event) =>
                                        setPassword(event.target.value)
                                    }
                                />

                            </div>

                            {error && (
                                <div className="login-error">
                                    {error}
                                </div>
                            )}

                            <button
                                type="submit"
                                disabled={loading}
                            >
                                {loading
                                    ? "Signing in..."
                                    : "Sign in"}
                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default Login;