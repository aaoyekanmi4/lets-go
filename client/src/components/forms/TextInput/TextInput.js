import React, { useState } from "react";

const TextInput = ({
  type,
  name,
  label,
  id,
  onChange,
  value,
  error,
  onBlur,
}) => {
  const [touched, setTouched] = useState(false);

  const getInputClass = () => {
    if (error) {
      return "Form__input Form__input--error";
    }

    return "Form__input Form__input--normal";
  };

  return (
    <div className="TextInput Form__group">
      <label className="Form__label" htmlFor={id}>
        {label}
      </label>
      <input
        className={getInputClass()}
        type={type}
        value={value}
        name={name}
        onChange={onChange}
        onBlur={() => {
          setTouched(true);
          onBlur();
        }}
        id={id}
      />
      <span className="Form__error">{error}</span>
    </div>
  );
};

export default TextInput;
