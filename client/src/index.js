import React from "react";
import ReactDOM from "react-dom/client";
import { Provider } from "react-redux";
import {
  legacy_createStore as createStore,
  applyMiddleware,
  compose,
} from "redux";
import ReduxThunk from "redux-thunk";

import combinedReducers from "./reducers";
import App from "./App.js";
import "./styles/main.scss";
import reportWebVitals from "./reportWebVitals";

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const store = createStore(
  combinedReducers,
  composeEnhancers(applyMiddleware(ReduxThunk))
);

const rootElement = document.getElementById("root");

const root = ReactDOM.createRoot(rootElement);

root.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>
);

reportWebVitals();
