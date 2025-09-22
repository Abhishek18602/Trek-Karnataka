import React, { useState, useEffect } from "react";

function App() {
  const [treks, setTreks] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchTreks = async () => {
      try {
        const response = await fetch("https://trek-karnataka.onrender.com");
        const data = await response.json();
        setTreks(data);
      } catch (error) {
        console.error("Error fetching treks:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchTreks();
  }, []);

  if (loading) {
    return <div style={styles.loading}>Loading Treks...</div>;
  }

  return (
    <div style={styles.container}>
      <h1 style={styles.heading}>Top Trekking places of Karnataka</h1>
     
     
      {treks.map((trek, index) => (
        <div key={index} style={styles.card}>
          <h2 style={styles.trekName}>{trek.name}</h2>
         
          <p>
            <strong>Title:</strong> {trek.title}
          </p>
          <p>
            <strong>Description:</strong>
            <br />
            {trek.description.split("\n").map((line, i) => (
              <span key={i}>
                {line}
                <br />
              </span>
            ))}
          </p>
        </div>
      ))}


    </div>
  );
}

const styles = {
  container: {
    maxWidth: "900px",
    margin: "0 auto",
    padding: "20px",
    fontFamily: "Arial, sans-serif",
  },
  heading: {
    textAlign: "center",
    marginBottom: "30px",
    color: "#2c3e50",
  },
  card: {
    background: "#f9f9f9",
    padding: "20px",
    marginBottom: "20px",
    borderRadius: "50px",
    boxShadow: "0 3px 6px rgba(0,0,0,0.1)",
  },
  trekName: {
    color: "#e74c3c",
  },
  loading: {
    textAlign: "center",
    marginTop: "50px",
    fontSize: "20px",
  },
};

export default App;
