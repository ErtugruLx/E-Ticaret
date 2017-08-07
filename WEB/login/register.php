<?php

require_once 'include/DB_Fonksiyon.php';
$database = new DB_Fonksiyon();

$response = array("error" => FALSE);

if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {

    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    if ($database->userCheck($email)) {
        $response["error"] = TRUE;
        $response["error_msg"] = "Kullanıcı zaten sisteme kayıtlı! " . $email;
        echo json_encode($response);
    } else {
        $user = $database->userRegister($name, $email, $password);
        if ($user) {
            $response["error"] = FALSE;
            $response["uid"] = $user["unique_id"];
            $response["user"]["name"] = $user["name"];
            $response["user"]["email"] = $user["email"];
            $response["user"]["created_at"] = $user["created_at"];
            $response["user"]["updated_at"] = $user["updated_at"];
            echo json_encode($response);
        } else {
            $response["error"] = TRUE;
            $response["error_msg"] = "Bilinmeyen bir hata oluştu!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Gerekli parametreler (isim, email veya şifre) eksik!";
    echo json_encode($response);
}
?>

