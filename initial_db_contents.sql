------------ Old table deletion ------------

DROP TABLE IF EXISTS `order_product`;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `user`;

------------ Table creation ------------

CREATE TABLE IF NOT EXISTS `user`
(
    `id`       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    `passcode` TEXT UNIQUE,
    `is_staff` INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS `product`
(
    `name`        TEXT,
    `description` TEXT,
    `vendor`      TEXT,
    `slug`        TEXT NOT NULL UNIQUE,
    `sku`         TEXT NOT NULL PRIMARY KEY,
    `price`       DECIMAL(10, 2),
    `img_url`     TEXT DEFAULT 'https://demofree.sirv.com/nope-not-here.jpg'
);

CREATE TABLE IF NOT EXISTS `cart`
(
    `user_id`     INTEGER NOT NULL,
    `product_sku` TEXT    NOT NULL,
    `qt`          INTEGER NOT NULL DEFAULT 1,
    PRIMARY KEY (`user_id`, `product_sku`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    FOREIGN KEY (`product_sku`) REFERENCES `product` (`sku`)
);

CREATE TABLE IF NOT EXISTS `order`
(
    `id`               INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    `user_id`          INTEGER,
    `shipping_address` TEXT,
    `tracking_num`     TEXT,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `order_product`
(
    `order_id`    INTEGER NOT NULL,
    `product_sku` TEXT    NOT NULL,
    `qt`          INTEGER,
    PRIMARY KEY (`order_id`, `product_sku`),
    FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
    FOREIGN KEY (`product_sku`) REFERENCES `product` (`sku`)
);

------------ Initial data insertion ------------

INSERT INTO `user`
VALUES (103, 'secret', 1),
       (104, 'qwerty', 0),
       (105, 'admin', 1),
       (106, 'us3r', 0);

INSERT INTO `product`
VALUES ('Cat Tower', 'Multi-level cat tower with scratching posts and cozy perches.', 'Kitty Haven', 'cat-tower',
        'AB123', 79.99, 'https://i.pinimg.com/564x/18/56/ae/1856aef7a0961513ab4bf85f92aa081d.jpg'),
       ('Cat Food Bowl', 'Stylish ceramic cat food bowl with fish design.', 'Purrfect Supplies', 'cat-food-bowl',
        'CD456', 9.99, 'https://i.pinimg.com/564x/d5/0c/c1/d50cc15eedf9192ec126de225d7538d1.jpg'),
       ('Portrait of Luna', 'Print of the cat queen Luna. Measures 30x30 inches.', 'Lunar Shadow', 'luna-portrait',
        'LU441', 39.99, 'https://i.etsystatic.com/42801559/r/il/98915b/5245110747/il_1588xN.5245110747_r5cb.jpg'),
       ('Catnip Crochet Toy', 'Random color. Measures 2 inches long. Comes with catnip sachet.', 'PetCo',
        'catnip-crochet-toy', 'PE125', 14.22,
        'https://i.etsystatic.com/20292668/r/il/224457/4737848035/il_1588xN.4737848035_ne6v.jpg'),
       ('Cat Recovery Suit', 'One size fits all. Cat not included.', 'Purrito', 'cat-recovery-suit', 'PU944', 19.99,
        'https://i.etsystatic.com/33459047/r/il/81260e/4453593375/il_fullxfull.4453593375_260e.jpg');

INSERT INTO `cart`
VALUES (103, 'AB123', 1),
       (104, 'AB123', 8),
       (104, 'CD456', 6),
       (104, 'PU944', 1);

INSERT INTO `order`
VALUES (10001, 103, 'hello, hello, world pls', 'AD12412L96'),
       (10002, 106, 'hello, hello, hello hello', '123'),
       (10003, 106, '123, 123, 123 123', NULL),
       (10004, 103, '111 Ravel St, Montreal, Canada 1A12S3', NULL);

INSERT INTO `order_product`
VALUES (10001, 'AB123', 1),
       (10001, 'CD456', 1),
       (10002, 'PU944', 1),
       (10003, 'AB123', 1),
       (10003, 'LU441', 1),
       (10004, 'PE125', 2);