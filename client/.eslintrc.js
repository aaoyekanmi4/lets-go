module.exports = {
  env: {
    browser: true,
    node: true,
    jest: true,
  },
  extends: [
    "eslint:recommended",
    "plugin:react/recommended",
    "plugin:prettier/recommended",
  ],
  parserOptions: {
    ecmaFeatures: {
      jsx: true,
    },
    ecmaVersion: 12,
    sourceType: "module",
  },
  plugins: ["react"],
  rules: {
    "react/react-in-jsx-scope": "off",
    // this allows us to disable rules(off) or enforce errors for the rule guidelines we are extending from
    "prettier/prettier": "error", // enforce prettier rules
    quotes: "off", // turns off now allowing me to use double quotes
    "no-console": "off",
    "no-unused-vars": "warn",
    "max-len": "off", // turning off max length when you write comments
    "func-names": "off", // turning off not allowing you to write anonymous functions aka functions without names
    "no-process-exit": "off", // this is a node thing
    "object-shorthand": "off", // turning off not allowing you to write methods like dog: function(){} inside objects
    "class-methods-use-this": "off", // turning off forces you to use this inside classes
    "no-plusplus": "off", // turning off not allowing me to do ++
    "react/prop-types": 0,
  },
};
