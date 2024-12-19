import { DynamicAppIconRegistry } from "./types";

export type IconName = DynamicAppIconRegistry["IconName"];

export function setAppIcon(
  name: IconName | null
): IconName | "DEFAULT" | false {
  console.error("setAppIcon is not supported on web");
  return false;
}

export function getAppIcon(): IconName | "DEFAULT" {
  console.error("getAppIcon is not supported on web");
  return "";
}
