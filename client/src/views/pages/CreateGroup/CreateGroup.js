import React from "react";
import { useSelector } from "react-redux";

import { defaultGroupValues } from "../../../components/forms/defaultValues.js";
import Header from "../../../components/Header/Header.js";
import GroupForm from "../../../components/forms/GroupForm/GroupForm.js";
import "../../sharedStyles/formPage.scss";

const CreateGroup = () => {
  const contacts = useSelector((state) => {
    return state.contacts;
  });

  return (
    <div className="CreateGroup">
      <Header />
      <main className="form-page__main">
        <GroupForm
          contacts={contacts}
          initialFormValues={defaultGroupValues}
          type="create"
        />
      </main>
    </div>
  );
};

export default CreateGroup;
