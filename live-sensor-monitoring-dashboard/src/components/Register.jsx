import { useState } from "react";
import { registerUser } from "../services/api";

function Register({ onRegisterSuccess, onBackToLogin }) {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] =
        useState("");

    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {

        event.preventDefault();

        setError("");
        setSuccess("");

        if (!email || !password || !confirmPassword) {
            setError("All fields are required");
            return;
        }

        if (password !== confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        try {

            setLoading(true);

            await registerUser(
                email,
                password
            );

            setSuccess(
                "Registration successful. You can now sign in."
            );

            setEmail("");
            setPassword("");
            setConfirmPassword("");

            setTimeout(() => {
                onRegisterSuccess();
            }, 1000);

        } catch (error) {

            setError(
                error.message ||
                "Registration failed"
            );

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

                        <h2>Create account</h2>

                        <p className="login-subtitle">
                            Register to monitor your sensors
                        </p>

                        <form onSubmit={handleSubmit}>

                            <div className="form-group">

                                <label htmlFor="register-email">
                                    Email
                                </label>

                                <input
                                    id="register-email"
                                    type="email"
                                    placeholder="Enter your email"
                                    value={email}
                                    onChange={(event) =>
                                        setEmail(event.target.value)
                                    }
                                />

                            </div>

                            <div className="form-group">

                                <label htmlFor="register-password">
                                    Password
                                </label>

                                <input
                                    id="register-password"
                                    type="password"
                                    placeholder="Enter your password"
                                    value={password}
                                    onChange={(event) =>
                                        setPassword(event.target.value)
                                    }
                                />

                            </div>

                            <div className="form-group">

                                <label htmlFor="confirm-password">
                                    Confirm Password
                                </label>

                                <input
                                    id="confirm-password"
                                    type="password"
                                    placeholder="Confirm your password"
                                    value={confirmPassword}
                                    onChange={(event) =>
                                        setConfirmPassword(event.target.value)
                                    }
                                />

                            </div>

                            {error && (
                                <div className="login-error">
                                    {error}
                                </div>
                            )}

                            {success && (
                                <div className="register-success">
                                    {success}
                                </div>
                            )}

                            <button
                                type="submit"
                                disabled={loading}
                            >
                                {loading
                                    ? "Creating account..."
                                    : "Register"}
                            </button>

                        </form>

                        <div className="register-switch">

                            <span>
                                Already have an account?
                            </span>

                            <button
                                type="button"
                                onClick={onBackToLogin}
                                className="text-button"
                            >
                                Sign in
                            </button>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default Register;
