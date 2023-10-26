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

  const login = (data: any) => {
    console.log("TRYING LOGIN");
    LoginApi.post(
      "/login",
      {
        username: data.username,
        password: data.pwd,
      },
      { withCredentials: true }
    )
      .then((res) => {
        setCookie(
          "userDetails",
          // ! COULD ENCODE WITH SECRET, LIKE JWT
          btoa(
            JSON.stringify({
              username: data.username,
              password: data.pwd,
            })
          )
        );
        window.location.replace("/");
      })
      .catch((err) => {
        setError(true);
        console.log(err);
      });
  };

  return (
    <form onSubmit={handleSubmit(login)}>
      <input
        type="text"
        placeholder="Email"
        id="username"
        {...register("username", { required: true })}
      />
      <input
        type="password"
        placeholder="Password"
        id="pwd"
        {...register("pwd", { required: true })}
      />
      {error && <p style={{ color: "red" }}>Invalid Password or Username.</p>}
      {errors.username && (
        <p style={{ color: "red" }}>Please use a valid email</p>
      )}
      {errors.pwd && <p style={{ color: "red" }}>Password is required</p>}
      <input type="submit" value={"LOGIN"}/>
    </form>
  );
}

export default Login;
