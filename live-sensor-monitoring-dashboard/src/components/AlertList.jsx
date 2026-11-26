function AlertList({ alerts }) {

    const activeAlerts =
        alerts.filter(alert => alert.active);

    return (
        <div className="alerts-section">

            <div className="section-header">

                <div>
                    <h2>Alerts</h2>

                    <p>
                        Current sensor conditions
                    </p>
                </div>

                <span className="alert-count">
                    {activeAlerts.length} active
                </span>

            </div>

            {activeAlerts.length === 0 ? (

                <div className="no-alerts">
                    No active alerts
                </div>

            ) : (

                <div className="alert-list">

                    {activeAlerts.map((alert, index) => (

                        <div
                            className="alert-item"
                            key={`${alert.sensorId}-${alert.type}-${index}`}
                        >

                            <div className="alert-icon">
                                !
                            </div>

                            <div className="alert-content">

                                <div className="alert-title">

                                    {alert.message}

                                </div>

                                <div className="alert-details">

                                    {alert.sensorId}

                                    <span>
                                        •
                                    </span>

                                    {new Date(
                                        alert.timestamp
                                    ).toLocaleString()}

                                </div>

                            </div>

                            <span className="alert-status">
                                ACTIVE
                            </span>

                        </div>

                    ))}

                </div>
            )}

        </div>
    );
}

export default AlertList;