import { Loader2 } from "lucide-react";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary" | "danger" | "ghost";
  size?: "sm" | "md" | "lg";
  loading?: boolean;
  icon?: React.ReactNode;
}

const variantMap = {
  primary:   "bg-blue-600 hover:bg-blue-700 text-white disabled:bg-blue-400",
  secondary: "bg-gray-100 hover:bg-gray-200 text-gray-700 dark:bg-gray-700 dark:hover:bg-gray-600 dark:text-gray-200",
  danger:    "bg-red-600 hover:bg-red-700 text-white disabled:bg-red-400",
  ghost:     "hover:bg-gray-100 dark:hover:bg-gray-800 text-gray-600 dark:text-gray-400",
};

const sizeMap = {
  sm: "px-3 py-1.5 text-xs",
  md: "px-4 py-2 text-sm",
  lg: "px-6 py-3 text-base",
};

const Button = ({
  variant = "primary",
  size = "md",
  loading = false,
  icon,
  children,
  className = "",
  disabled,
  ...props
}: ButtonProps) => (
  <button
    disabled={loading || disabled}
    className={`inline-flex items-center gap-2 font-medium rounded-lg transition-all ${variantMap[variant]} ${sizeMap[size]} ${className}`}
    {...props}
  >
    {loading ? <Loader2 size={14} className="animate-spin" /> : icon}
    {children}
  </button>
);

export default Button;