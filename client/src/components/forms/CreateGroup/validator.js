import { validateName, validateContacts } from "../validators.js";

//takes in a formErrors object that is constant being modified
const validateField = (formValues, formErrors, fieldName) => {
  if (fieldName === "name") {
    return validateName(formValues, formErrors);
  }

  if (fieldName === "contacts") {
    return validateContacts(formValues, formErrors);
  }
};

export { validateField };
