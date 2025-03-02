import ExpoModulesCore

public class ExpoDynamicAppIconModule: Module {
  public func definition() -> ModuleDefinition {

    Name("ExpoDynamicAppIcon")

    Function("setAppIcon") { (name: String?,isInBackground: Bool) -> String in
      self.setAppIcon(name, isInBackground)

      // Return "DEFAULT" if name is nil or empty
      return name ?? "DEFAULT"
    }

    Function("getAppIcon") { () -> String in
      // Return the current alternate icon name or "DEFAULT" if none is set
      let iconName = UIApplication.shared.alternateIconName
      return iconName?.replacingOccurrences(of: "AppIcon-", with: "") ?? "DEFAULT"
    }
  }

private func setAppIcon(_ iconName: String?,_ isInBackground: Bool = true) {
    if UIApplication.shared.responds(to: #selector(getter: UIApplication.supportsAlternateIcons)) &&
        UIApplication.shared.supportsAlternateIcons {

       var iconNameToUse: String? = nil  
        if let iconName = iconName, !iconName.isEmpty {
            iconNameToUse = "AppIcon-\(iconName)"
        }

        if isInBackground {
         
          typealias SetAlternateIconName = @convention(c) (NSObject, Selector, NSString?, @escaping (NSError?) -> ()) -> ()
          
          let selectorString = "_setAlternateIconName:completionHandler:"
          let selector = NSSelectorFromString(selectorString)
          
          if let methodIMP = UIApplication.shared.method(for: selector) {
              let method = unsafeBitCast(methodIMP, to: SetAlternateIconName.self)
              method(UIApplication.shared, selector, iconNameToUse as NSString?) { error in
                  if let error = error {
                      print("Failed to set app icon: \(error.localizedDescription)")
                  }
              }
          }
        }else{

          UIApplication.shared.setAlternateIconName(
            iconNameToUse,
            completionHandler: { error in
              if let error = error {
                // Handle error if necessary
                print("Failed to set app icon: \(error.localizedDescription)")
              }
            })
          }
    }
}

}
