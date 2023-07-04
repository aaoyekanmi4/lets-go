import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";

// import { getEditContactFunction, findContact } from "./helpers.js";
import { findGroup } from "./helpers.js";
import { defaultGroupValues } from "../../../components/forms/defaultValues.js";
import Header from "../../../components/Header/Header.js";
import GroupForm from "../../../components/forms/GroupForm/GroupForm.js";
import "../../sharedStyles/formPage.scss";

const EditGroup = () => {
  const { groupId } = useParams();

  const contacts = useSelector((state) => {
    return state.contacts;
  });

  const user = useSelector((state) => {
    return state.user;
  });

  const [formValues, setFormValues] = useState(defaultGroupValues);

  const [errors, setErrors] = useState(null);

  useEffect(() => {
    findGroup(user.jwtToken, groupId, setFormValues, setErrors);
  }, []);

  // const editContact = getEditContactFunction(contactId);

  return (
    <div className="EditGroup">
      <Header />
      <main className="form-page__main">
        <GroupForm
          initialFormValues={formValues}
          type="edit"
          contacts={contacts}
          // sendData={editContact}
        />
      </main>
    </div>
  );
};

export default EditGroup;
