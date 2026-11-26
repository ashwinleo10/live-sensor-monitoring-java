import { useEffect, useState } from "react";
import {
    getLatestReadings,
    getSensors,
    getAlerts,
    logoutUser,
} from "../services/api";

import SensorCard from "./SensorCard";
import AlertList from "./AlertList";

function Dashboard({ onLogout }) {

    const [sensors, setSensors] = useState([]);
    const [readings, setReadings] = useState([]);
    const [alerts, setAlerts] = useState([]);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const fetchDashboardData = async () => {

        try {

            setError("");

            const [
                sensorData,
                readingData,
                alertData,
            ] = await Promise.all([
                getSensors(),
                getLatestReadings(),
                getAlerts(),
            ]);

            setSensors(sensorData);
            setReadings(readingData);
            setAlerts(alertData);

        } catch (error) {

            console.error(
                "Dashboard data error:",
                error
            );

            if (error.message === "Session expired") {

                onLogout();

                return;
            }

            setError(
                "Unable to load dashboard data"
            );

        } finally {

            setLoading(false);
        }
    };

    useEffect(() => {

        fetchDashboardData();

        const intervalId =
            setInterval(
                fetchDashboardData,
                5000
            );

        return () => {
            clearInterval(intervalId);
        };

    }, []);

    const getReadingForSensor = (sensorId) => {

        return readings.find(
            reading =>
                reading.sensorId === sensorId
        );
    };

    const handleLogout = async () => {

        try {

            await logoutUser();

        } catch (error) {

            console.error(
                "Logout error:",
                error
            );

            localStorage.removeItem(
                "jwtToken"
            );

        } finally {

            onLogout();
        }
    };

    if (loading) {

        return (
            <div className="dashboard-loading">
                Loading sensor data...
            </div>
        );
    }

    return (
        <div className="dashboard">

            <header className="dashboard-header">

                <div>
                    <h1>
                        Sensor Monitoring
                    </h1>

                    <p>
                        Live Sensor Monitoring Dashboard
                    </p>
                </div>

                <button
                    className="logout-button"
                    onClick={handleLogout}
                >
                    Logout
                </button>

            </header>

            {error && (
                <div className="dashboard-error">
                    {error}
                </div>
            )}

            <main className="dashboard-content">

                <section className="sensors-section">

                    <div className="section-header">

                        <div>
                            <h2>
                                Sensors
                            </h2>

                            <p>
                                Live sensor readings
                            </p>
                        </div>

                        <span className="live-indicator">
                            <span className="live-dot"></span>
                            Live
                        </span>

                    </div>

                    <div className="sensor-grid">

                        {sensors.map(sensor => (

                            <SensorCard
                                key={sensor.sensorId}
                                sensor={sensor}
                                reading={getReadingForSensor(
                                    sensor.sensorId
                                )}
                            />

                        ))}

                    </div>

                </section>

                <AlertList
                    alerts={alerts}
                />

            </main>

        </div>
    );
}

export default Dashboard;