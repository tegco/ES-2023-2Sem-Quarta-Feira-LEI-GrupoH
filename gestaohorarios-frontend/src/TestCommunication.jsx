import React, { useEffect, useState } from 'react';

const TestCommunication = () => {
  const [message, setMessage] = useState('');

  useEffect(() => {
    fetch('/api/hello')
      .then((response) => response.text())
      .then((data) => setMessage(data));
  }, []);

  return (
    <div>
      <h1>Backend message:</h1>
      <p>{message}</p>
    </div>
  );
};

export default TestCommunication;