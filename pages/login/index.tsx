import { LoginApi } from "@/db/LoginApi";
import { setCookie } from "cookies-next";
import { redirect } from "next/navigation";
import React, { useState } from "react";
import { useForm } from "react-hook-form";

type Props = {};

function Login({}: Props) {
  const {
    handleSubmit,
    register,
    formState: { errors },
  } = useForm();
  const [error, setError] = useState(false);

  const login = async (data: any) => {
    console.log("TRYING LOGIN");
    await LoginApi.post(
      "/login",
      {
        username: data.username,
        password: data.pwd,
      },
      { withCredentials: true }
    )
      .then(() => {
        LoginApi.get("/profile", {
          auth: {
            username: data.username,
            password: data.pwd,
          },
        }).then((res) => {
          setCookie(
            "userDetails",
            // ! COULD ENCODE WITH SECRET, LIKE JWT
            btoa(
              JSON.stringify({
                username: data.username,
                password: data.pwd,
                roles: res.data.authorities,
                create_dt: res.data.create_dt,
              })
            )
          );
          window.location.replace("/");
        });
      })
      .catch((err) => {
        setError(true);
        console.log(err);
      });
  };

  return (
    <form
      style={{
        display: "flex",
        flexDirection: "column",
        gap: 10,
        padding: 20,
        borderRadius: 10,
        background: "#8f8",
        border: "1px solid black",
      }}
      onSubmit={handleSubmit(login)}
    >
      <h2 style={{ fontSize: 30 }}>Sign In</h2>
      <br />
      {error && (
        <p
          style={{
            color: "red",
            padding: 5,
            background: "#fff",
            borderRadius: 10,
            fontSize: 23,
            border: "1px solid black",
          }}
        >
          Invalid Password or Username.
        </p>
      )}
      <input
        style={{
          borderRadius: 10,
          border: "1px solid black",
          padding: 5,
          background: "#fff",
        }}
        type="text"
        placeholder="Email"
        id="username"
        {...register("username", { required: true })}
      />
      {errors.username && (
        <p
          style={{
            color: "red",
            padding: 5,
            background: "#fff",
            borderRadius: 10,
            fontSize: 23,
            border: "1px solid black",
          }}
        >
          Please use a valid email
        </p>
      )}
      <input
        style={{
          borderRadius: 10,
          border: "1px solid black",
          padding: 5,
          background: "#fff",
        }}
        type="password"
        placeholder="Password"
        id="pwd"
        {...register("pwd", { required: true })}
      />

      {errors.pwd && (
        <p
          style={{
            color: "red",
            padding: 5,
            background: "#fff",
            borderRadius: 10,
            fontSize: 23,
            border: "1px solid black",
          }}
        >
          Password is required
        </p>
      )}
      <input
        style={{
          cursor: "pointer",
          borderRadius: 10,
          border: "1px solid black",
          padding: 5,
          background: "#fff",
        }}
        type="submit"
        value={"LOGIN"}
      />
    </form>
  );
}

export default Login;
