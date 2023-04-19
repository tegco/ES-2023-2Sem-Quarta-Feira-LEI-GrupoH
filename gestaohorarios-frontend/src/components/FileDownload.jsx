import React, { useState } from "react";
import axios from "axios";

const FileDownloadComponent = () => {
  const [fileName, setFileName] = useState("");

  const handleDownload = async () => {
    try {
      const response = await axios.get(`/api/v1/horario/downloadFile/${fileName}`, {
        responseType: "blob", // Set response type to blob to handle binary data
      });

      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      console.log(fileName)
      link.href = url;
      link.setAttribute("download", fileName);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error(error);
      // Handle error
    }
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Enter file name"
        value={fileName}
        onChange={(e) => setFileName(e.target.value)}
      />
      <button onClick={handleDownload}>Download File</button>
    </div>
  );
};

export default FileDownloadComponent;
