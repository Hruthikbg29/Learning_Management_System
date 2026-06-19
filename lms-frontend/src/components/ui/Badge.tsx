interface BadgeProps {
  label: string;
  variant?: "blue" | "green" | "red" | "yellow" | "gray" | "purple";
}

const variantMap = {
  blue:   "bg-blue-100 text-blue-700 dark:bg-blue-900/30 dark:text-blue-400",
  green:  "bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400",
  red:    "bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400",
  yellow: "bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400",
  gray:   "bg-gray-100 text-gray-600 dark:bg-gray-800 dark:text-gray-400",
  purple: "bg-purple-100 text-purple-700 dark:bg-purple-900/30 dark:text-purple-400",
};

const Badge = ({ label, variant = "gray" }: BadgeProps) => (
  <span className={`
    inline-flex items-center px-2.5 py-0.5
    rounded-full text-xs font-medium
    ${variantMap[variant]}
  `}>
    {label}
  </span>
);

export default Badge;