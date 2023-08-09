import React from "react";
import { useParams } from "react-router";

function UserDetail() {
  const { id } = useParams();

  return (
    <>
      <h2>User Detail</h2>
      <span>user {id}</span>
    </>
  );
}

export default UserDetail;
