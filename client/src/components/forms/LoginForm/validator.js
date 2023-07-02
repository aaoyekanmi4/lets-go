import validator from "validator";

//takes in a formErrors object that is constantly being modified
//so since being used to update formErrors state, pass in a brand new
//form Errors object
const validateField = (formValues, formErrors, fieldName) => {
  if (fieldName === "username") {
    if (!formValues.username) {
      formErrors.username = "Required";
    } else {
      delete formErrors.username;
    }

    return formErrors;
  }

  if (fieldName === "password") {
    if (!formValues.password) {
      formErrors.password = "Required";
    } else {
      delete formErrors.password;
    }

    return formErrors;
  }
};

export { validateField };
