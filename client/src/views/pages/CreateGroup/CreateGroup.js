import React from "react";
import { useSelector } from "react-redux";

import Header from "../../../components/Header/Header.js";
import CreateGroupForm from "../../../components/forms/CreateGroupForm/CreateGroupForm.js";
import "../../sharedStyles/formPage.scss";

const CreateGroup = () => {
  const contacts = useSelector((state) => {
    return state.contacts;
  });

  return (
    <div className="CreateGroup">
      <Header />
      <main className="form-page__main">
        <CreateGroupForm contacts={contacts} />
      </main>
    </div>
  );
};

export default CreateGroup;
