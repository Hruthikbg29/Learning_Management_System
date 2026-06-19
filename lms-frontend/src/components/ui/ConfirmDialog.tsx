import { AlertTriangle } from "lucide-react";
import Modal from "./Modal";
import Button from "./Button";

interface ConfirmDialogProps {
  isOpen:       boolean;
  onClose:      () => void;
  onConfirm:    () => void;
  title:        string;
  message:      string;
  confirmLabel?: string;
  cancelLabel?:  string;
  loading?:      boolean;
  variant?:      "danger" | "warning";
}

const ConfirmDialog = ({
  isOpen,
  onClose,
  onConfirm,
  title,
  message,
  confirmLabel = "Confirm",
  cancelLabel  = "Cancel",
  loading      = false,
  variant      = "danger",
}: ConfirmDialogProps) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose} title={title} size="sm">
      <div className="flex flex-col items-center text-center">

        {/* Icon */}
        <div className={`
          w-14 h-14 rounded-full flex items-center justify-center mb-4
          ${variant === "danger"
            ? "bg-red-50 dark:bg-red-900/20"
            : "bg-yellow-50 dark:bg-yellow-900/20"
          }
        `}>
          <AlertTriangle
            size={24}
            className={variant === "danger" ? "text-red-500" : "text-yellow-500"}
          />
        </div>

        {/* Message */}
        <p className="text-sm text-gray-500 dark:text-gray-400 mb-6">
          {message}
        </p>

        {/* Buttons */}
        <div className="flex gap-3 w-full">
          <Button
            variant="secondary"
            className="flex-1"
            onClick={onClose}
            disabled={loading}
          >
            {cancelLabel}
          </Button>
          <Button
            variant={variant === "danger" ? "danger" : "primary"}
            className="flex-1"
            onClick={onConfirm}
            loading={loading}
          >
            {confirmLabel}
          </Button>
        </div>
      </div>
    </Modal>
  );
};

export default ConfirmDialog;