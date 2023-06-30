//takes in a formErrors object that is constant being modified
const validateField = (formValues, formErrors, fieldName) => {
  if (fieldName === "name") {
    if (!formValues.name) {
      formErrors["name"] = "Required";
    } else {
      delete formErrors["name"];
    }
    return formErrors;
  }

  if (fieldName === "contacts") {
    if (!formValues.contacts.length) {
      formErrors["contacts"] = "Must add at least 1 contact";
    } else {
      delete formErrors["contacts"];
    }
    return formErrors;
  }
};

const validateAllFields = (formValues, updateErrorsFunction) => {
  const formErrors = {};

  Object.keys(formValues).forEach((field) => {
    validateField(formValues, formErrors, field, updateErrorsFunction);
  });

  updateErrorsFunction(formErrors);
};

export { validateField, validateAllFields };
