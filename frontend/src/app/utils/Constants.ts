export class Constants {
  //keys
  static readonly ACCESS_TOKEN_KEY = "accessToken";
  static readonly ADMIN_ROLE_KEY = "ADMIN";
  static readonly USER_ROLE_KEY = "USER";

  //pages
  static readonly LOGIN_PAGE: string  = "/login"
  static readonly REGISTER_PAGE: string = "/register"
  static readonly HOME_PAGE: string = "/home"
  static readonly ADMIN_PAGE: string = "/admin-page"
  static readonly PROFILE_PAGE: string = "/profile-page"

  //userApi
  static readonly ACCOUNT_DETAILS_URL = "/users/me";

  //carApi
  static readonly ADD_CAR_REQUEST_URL = "/cars/car"
  static readonly REMOVE_CAR_REQUEST_URL = "/cars/car/"
  static readonly GET_ALL_CARS_REQUEST_URL = "/cars"
  static readonly ADD_CAR_PHOTO_REQUEST_URL = "/cars/car/photo/"

  //filterApi
  static readonly GET_MODELS_REQUEST_URL = "/filter/models"
  static readonly GET_BRANDS_REQUEST_URL = "/filter/brands"

}
