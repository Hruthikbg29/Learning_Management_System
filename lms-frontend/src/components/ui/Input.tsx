import { forwardRef } from "react";
import { LucideIcon } from "lucide-react";

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?:       string;
  error?:       string;
  icon?:        LucideIcon;
  helperText?:  string;
}

const Input = forwardRef<HTMLInputElement, InputProps>(
  ({ label, error, icon: Icon, helperText, className = "", ...props }, ref) => {
    return (
      <div className="w-full">

        {/* Label */}
        {label && (
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            {label}
          </label>
        )}

        {/* Input wrapper */}
        <div className="relative">
          {Icon && (
            <div className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
              <Icon size={16} />
            </div>
          )}
          <input
            ref={ref}
            className={`
              w-full px-3 py-2.5 rounded-lg border text-sm transition
              bg-white dark:bg-gray-700
              text-gray-900 dark:text-white
              placeholder-gray-400 dark:placeholder-gray-500
              focus:outline-none focus:ring-2
              ${error
                ? "border-red-400 focus:ring-red-400"
                : "border-gray-300 dark:border-gray-600 focus:ring-blue-500"
              }
              ${Icon ? "pl-9" : ""}
              ${className}
            `}
            {...props}
          />
        </div>

        {/* Error message */}
        {error && (
          <p className="text-red-500 text-xs mt-1">{error}</p>
        )}

        {/* Helper text */}
        {helperText && !error && (
          <p className="text-gray-400 text-xs mt-1">{helperText}</p>
        )}
      </div>
    );
  }
);

Input.displayName = "Input";

export default Input;