import { LoginApi } from "@/db/LoginApi";
import { User } from "@/interface/User";
import { getCookie } from "cookies-next";
import React, { useEffect, useState } from "react";

type Props = {};

function index({}: Props) {
  const [users, setUsers] = useState<User[]>([]);
  const [username, setUserDetails] = useState("");
  useEffect(() => {
    const userDetails = JSON.parse(atob(getCookie("userDetails")!));
    setUserDetails(userDetails.username);
    LoginApi.get("/protected/user", {
      auth: {
        username: userDetails.username,
        password: userDetails.password,
      },
    }).then((res) => setUsers(res.data));
  }, []);

  return (
    <main style={{ display: "flex", flexWrap: "wrap", gap: 10 }}>
      {users.map((user) => (
        <div
          style={{
            padding: 30,
            backgroundColor: "#eee",
            borderRadius: 5,
          }}
        >
          {username === user.email && (
            <h3
              style={{
                marginBottom: 10,
                borderRadius: 5,
                backgroundColor: "#9e9",
                padding: 10,
                textAlign: "center",
              }}
            >
              YOU
            </h3>
          )}
          <ul>
            <li>
              <b>Email: </b>
              {user.email}
            </li>
            <li>
              <b>Username: </b>
              {user.username}
            </li>
            <li>
              <b>Creation Date: </b>
              {user.create_dt.substring(0, 10)}
            </li>
            <li>
              <b>Roles: </b>
            </li>
            <ol>
              {user.authorities.map((auth) => (
                <li>{auth.name}</li>
              ))}
            </ol>
          </ul>
        </div>
      ))}
    </main>
  );
}

export default index;
