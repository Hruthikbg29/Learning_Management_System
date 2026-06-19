import { Navigate } from "react-router-dom";
import { TOKEN_KEY } from "../utils/constants";

interface Props {
  children: React.ReactNode;
}

const PrivateRoute = ({ children }: Props) => {
  const token = localStorage.getItem(TOKEN_KEY);
  return token ? <>{children}</> : <Navigate to="/login" replace />;
};

export default PrivateRoute;