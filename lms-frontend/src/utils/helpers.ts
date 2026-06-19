// ── Format date to readable string ────────────────────────────────────────
export const formatDate = (dateStr: string): string => {
  if (!dateStr) return "—";
  return new Date(dateStr).toLocaleDateString("en-IN", {
    day:   "2-digit",
    month: "short",
    year:  "numeric",
  });
};

// ── Format date with time ──────────────────────────────────────────────────
export const formatDateTime = (dateStr: string): string => {
  if (!dateStr) return "—";
  return new Date(dateStr).toLocaleString("en-IN", {
    day:    "2-digit",
    month:  "short",
    year:   "numeric",
    hour:   "2-digit",
    minute: "2-digit",
  });
};

// ── Truncate long text ─────────────────────────────────────────────────────
export const truncate = (text: string, maxLength: number = 50): string => {
  if (!text) return "—";
  return text.length > maxLength
    ? text.substring(0, maxLength) + "..."
    : text;
};

// ── Capitalize first letter ────────────────────────────────────────────────
export const capitalize = (text: string): string => {
  if (!text) return "";
  return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
};

// ── Get initials from name ─────────────────────────────────────────────────
export const getInitials = (name: string): string => {
  if (!name) return "?";
  return name
    .split(" ")
    .map((n) => n.charAt(0))
    .join("")
    .toUpperCase()
    .substring(0, 2);
};

// ── Check if token is expired ──────────────────────────────────────────────
export const isTokenExpired = (token: string): boolean => {
  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload.exp * 1000 < Date.now();
  } catch {
    return true;
  }
};

// ── Get enrollment status color ────────────────────────────────────────────
export const getStatusColor = (
  status: string
): "green" | "blue" | "red" | "gray" => {
  switch (status) {
    case "ACTIVE":    return "green";
    case "COMPLETED": return "blue";
    case "DROPPED":   return "red";
    default:          return "gray";
  }
};