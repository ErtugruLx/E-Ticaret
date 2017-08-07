<?php


require_once 'include/DB_Fonksiyon.php';
$database = new DB_Fonksiyon();

$response = array("error" => FALSE);

if (isset($_POST['email']) && isset($_POST['password'])) {

    $email = $_POST['email'];
    $password = $_POST['password'];

    $user = $database->checkEmailPassword($email, $password);

    if ($user != false) {
        $response["error"] = FALSE;
        $response["uid"] = $user["unique_id"];
        $response["user"]["name"] = $user["name"];
        $response["user"]["email"] = $user["email"];
        $response["user"]["created_at"] = $user["created_at"];
        $response["user"]["updated_at"] = $user["updated_at"];
        $response["user"]["yetki"]=$user["yetki"];
        echo json_encode($response);
    } else {
        $response["error"] = TRUE;
        $response["error_msg"] = "Giris bilgileri yanlıs. Lütfen tekrar deneyiniz!";
        echo json_encode($response);
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Gerekli parametreler (isim veya sifre) eksik!";
    echo json_encode($response);
}
?>

