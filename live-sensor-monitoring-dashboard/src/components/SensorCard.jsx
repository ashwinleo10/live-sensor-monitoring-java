function SensorCard({ sensor, reading }) {

    const isOnline = sensor?.status === "ONLINE";

    return (
        <div className="sensor-card">

            <div className="sensor-card-header">

                <div>
                    <h3>{sensor.sensorId}</h3>

                    <span
                        className={
                            isOnline
                                ? "status online"
                                : "status offline"
                        }
                    >
                        <span className="status-dot"></span>

                        {sensor.status}
                    </span>
                </div>

                <div className="sensor-label">
                    Sensor
                </div>

            </div>

            <div className="sensor-values">

                <div className="sensor-value">

                    <span className="value-label">
                        Temperature
                    </span>

                    <span className="value">
                        {reading
                            ? `${reading.temperature} °C`
                            : "--"}
                    </span>

                </div>

                <div className="sensor-value">

                    <span className="value-label">
                        Humidity
                    </span>

                    <span className="value">
                        {reading
                            ? `${reading.humidity} %`
                            : "--"}
                    </span>

                </div>

            </div>

            <div className="last-reading">

                Last reading:{" "}

                {sensor.lastReadingTime
                    ? new Date(
                        sensor.lastReadingTime
                    ).toLocaleString()
                    : "No reading available"}

            </div>

        </div>
    );
}

export default SensorCard;