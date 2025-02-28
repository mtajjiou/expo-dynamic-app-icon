import ExpoModulesCore

public class ExpoDynamicAppIconModule: Module {
  public func definition() -> ModuleDefinition {

    Name("ExpoDynamicAppIcon")

    Function("setAppIcon") { (name: String?) -> String in
      self.setAppIcon(name)

      // Return "DEFAULT" if name is nil or empty
      return name ?? "DEFAULT"
    }

    Function("getAppIcon") { () -> String in
      // Return the current alternate icon name or "DEFAULT" if none is set
      let iconName = UIApplication.shared.alternateIconName
      return iconName?.replacingOccurrences(of: "AppIcon-", with: "") ?? "DEFAULT"
    }
  }

private func setAppIcon(_ iconName: String?) {
    if UIApplication.shared.responds(to: #selector(getter: UIApplication.supportsAlternateIcons)) &&
        UIApplication.shared.supportsAlternateIcons {

        var iconNameToUse: NSString? = nil  
        if let iconName = iconName, !iconName.isEmpty {
            iconNameToUse = "AppIcon-\(iconName)" as NSString
        }


        typealias SetAlternateIconName = @convention(c) (NSObject, Selector, NSString?, @escaping (NSError?) -> ()) -> ()
        
        let selectorString = "_setAlternateIconName:completionHandler:"
        let selector = NSSelectorFromString(selectorString)
        
        if let methodIMP = UIApplication.shared.method(for: selector) {
            let method = unsafeBitCast(methodIMP, to: SetAlternateIconName.self)
            method(UIApplication.shared, selector, iconNameToUse) { error in
                if let error = error {
                    print("Failed to set app icon: \(error.localizedDescription)")
                }
            }
        }
    }
}

}
