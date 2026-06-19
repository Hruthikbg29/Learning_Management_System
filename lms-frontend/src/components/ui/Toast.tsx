import { useEffect } from "react";
import { CheckCircle, XCircle, AlertCircle, X } from "lucide-react";

interface ToastProps {
  type:     "success" | "error" | "warning";
  message:  string;
  onClose:  () => void;
  duration?: number;
}

const iconMap = {
  success: { icon: CheckCircle, color: "text-green-500",  bg: "bg-green-50 dark:bg-green-900/20",  border: "border-green-200 dark:border-green-800" },
  error:   { icon: XCircle,     color: "text-red-500",    bg: "bg-red-50 dark:bg-red-900/20",      border: "border-red-200 dark:border-red-800"     },
  warning: { icon: AlertCircle, color: "text-yellow-500", bg: "bg-yellow-50 dark:bg-yellow-900/20",border: "border-yellow-200 dark:border-yellow-800"},
};

const Toast = ({ type, message, onClose, duration = 3000 }: ToastProps) => {
  const { icon: Icon, color, bg, border } = iconMap[type];

  useEffect(() => {
    const timer = setTimeout(onClose, duration);
    return () => clearTimeout(timer);
  }, [onClose, duration]);

  return (
    <div className={`
      flex items-center gap-3 px-4 py-3
      rounded-xl border shadow-lg
      ${bg} ${border}
      animate-in slide-in-from-right-5
      max-w-sm w-full
    `}>
      <Icon size={18} className={`flex-shrink-0 ${color}`} />
      <p className="text-sm text-gray-700 dark:text-gray-300 flex-1">
        {message}
      </p>
      <button
        onClick={onClose}
        className="text-gray-400 hover:text-gray-600 dark:hover:text-gray-200 transition"
      >
        <X size={14} />
      </button>
    </div>
  );
};

export default Toast;