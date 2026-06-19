interface SpinnerProps {
  size?: "sm" | "md" | "lg";
  className?: string;
}

const sizeMap = {
  sm: "w-4 h-4 border-2",
  md: "w-8 h-8 border-4",
  lg: "w-12 h-12 border-4",
};

const Spinner = ({ size = "md", className = "" }: SpinnerProps) => (
  <div className={`
    ${sizeMap[size]}
    border-blue-600 border-t-transparent
    rounded-full animate-spin
    ${className}
  `} />
);

export default Spinner;