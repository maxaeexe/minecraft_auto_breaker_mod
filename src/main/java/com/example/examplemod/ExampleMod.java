package com.example.examplemod;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import java.sql.*;
import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mod("examplemod")
public class ExampleMod {
    private static final Logger LOGGER = LogManager.getLogger();

    // Veritabanı bağlantı bilgileri - BU BİLGİLERİ KENDİ VERİTABANINIZA GÖRE DEĞİŞTİRİN
    private static final String DB_URL = "jdbc:sqlserver://sizin bilgileriniz;databaseName=sizin bilgileriniz;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "sizin bilgileriniz";
    private static final String DB_PASSWORD = "sizin bilgileriniz";

    private static boolean modActive = false;
    private static boolean isAuthenticated = false;
    private static String currentUser = "";

    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    private static KeyBinding toggleKey;

    // Alan koordinatları
    private static final int x1 = 111, y1 = 47, z1 = 687;
    private static final int x2 = 80, y2 = 46, z2 = 656;

    private static BlockPos breakingTarget = null;
    private static boolean isAttacking = false;
    private static int currentYLevel = 47;

    private static boolean shouldMoveForward = false;
    private static BlockPos movementTarget = null;

    // Türkçe metinler için kodlar
    private static class Messages {
        public static final String MOD_ACTIVE = "\u00A7aMod: A\u00C7IK";
        public static final String MOD_INACTIVE = "\u00A7cMod: KAPALI";
        public static final String AUTH_REQUIRED = "\u00A7cGiri\u015F Gerekli";
        public static final String Y_LEVEL = "Y Seviye: ";
        public static final String TARGET_FOUND = "\u00A7eHedef: ";
        public static final String TARGET_SEARCHING = "\u00A7aHedef aran\u0131yor...";
        public static final String MOD_STARTED = "Mod ba\u015Flat\u0131ld\u0131!";
        public static final String MOD_STOPPED = "Mod durduruldu!";
        public static final String NO_PICKAXE = "Envanterde kazma bulunamad\u0131!";
        public static final String NO_BLOCKS = "K\u0131r\u0131lacak yosunlu k\u0131r\u0131kta\u015F kalmad\u0131!";
        public static final String LEVEL_DECREASED = "Y seviyesi d\u00FC\u015F\u00FCr\u00FCld\u00FC: ";
        public static final String LOGIN_SUCCESS = "Giri\u015F ba\u015Far\u0131l\u0131!";
        public static final String LOGIN_FAILED = "Kullan\u0131c\u0131 ad\u0131 veya \u015Fifre hatal\u0131!";
        public static final String DB_ERROR = "Veritaban\u0131 ba\u011Flant\u0131 hatas\u0131!";
        public static final String USER_LABEL = "Kullan\u0131c\u0131 Ad\u0131:";
        public static final String PASSWORD_LABEL = "\u015Eifre:";
        public static final String LOGIN_BUTTON = "Giri\u015F Yap";
        public static final String CANCEL_BUTTON = "\u0130ptal";
        public static final String WELCOME = "Ho\u015Fgeldin: ";
    }

    static {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            toggleKey = new KeyBinding("key.examplemod.toggle", GLFW.GLFW_KEY_F6, "key.categories.misc");
            ClientRegistry.registerKeyBinding(toggleKey);
        }
    }

    public ExampleMod() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            new Thread(() -> {
                try {
                    Thread.sleep(5000); 
                    testDatabaseConnection();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
    private void testDatabaseConnection() {
        try {
            LOGGER.info("=== DATABASE CONNECTION TEST ===");

            
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                LOGGER.info("✓ JDBC Driver yüklendi");
            } catch (ClassNotFoundException e) {
                LOGGER.error("✗ JDBC Driver yüklenemedi: " + e.getMessage());
                return;
            }

            
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                LOGGER.info("✓ Veritabanı bağlantısı başarılı");

                
                try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM maden_users")) {
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            int count = rs.getInt(1);
                            LOGGER.info("✓ Tablo erişimi başarılı, toplam kullanıcı: " + count);
                        }
                    }
                }
            }

            LOGGER.info("=== TEST COMPLETED SUCCESSFULLY ===");

        } catch (SQLException e) {
            LOGGER.error("✗ Database test failed: " + e.getMessage());
            LOGGER.error("SQLState: " + e.getSQLState());
            LOGGER.error("ErrorCode: " + e.getErrorCode());
        } catch (Exception e) {
            LOGGER.error("✗ Unexpected error: " + e.getMessage(), e);
        }
    }

    // Veritabanı bağlantısı ve kullanıcı doğrulama
    public static class DatabaseManager {

        public static CompletableFuture<Boolean> authenticateUser(String username, String password) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        String sql = "SELECT COUNT(*) FROM maden_users WHERE kadi = ? AND sifre = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setString(1, username);
                            stmt.setString(2, password);

                            try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next()) {
                                    boolean isValid = rs.getInt(1) > 0;
                                    if (isValid) {
                                        updateUserInfo(conn, username);
                                    }
                                    return isValid;
                                }
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    LOGGER.error("SQL Server JDBC driver bulunamadı!", e);
                } catch (SQLException e) {
                    LOGGER.error("Veritabanı hatası: " + e.getMessage(), e);
                }
                return false;
            }, executor);
        }

        private static void updateUserInfo(Connection conn, String username) throws SQLException {
            String updateSql = "UPDATE maden_users SET ip = ?, date = GETDATE() WHERE kadi = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                try {
                    String ip = InetAddress.getLocalHost().getHostAddress();
                    stmt.setString(1, ip);
                    stmt.setString(2, username);
                    stmt.executeUpdate();
                } catch (Exception e) {
                    LOGGER.warn("IP güncellenirken hata: " + e.getMessage());
                    stmt.setString(1, "Unknown");
                    stmt.setString(2, username);
                    stmt.executeUpdate();
                }
            }
        }
    }

    public static class LoginScreen extends Screen {
        private TextFieldWidget usernameField;
        private TextFieldWidget passwordField;
        private Button loginButton;
        private Button cancelButton;
        private String errorMessage = "";
        private boolean isLoading = false;

        public LoginScreen() {
            super(new StringTextComponent("Login"));
        }

        @Override
        protected void init() {
            super.init();

            int centerX = this.width / 2;
            int centerY = this.height / 2;

            this.usernameField = new TextFieldWidget(this.font, centerX - 100, centerY - 50, 200, 20,
                    new StringTextComponent("Username"));
            this.usernameField.setMaxLength(32);
            this.addButton(this.usernameField);

            this.passwordField = new TextFieldWidget(this.font, centerX - 100, centerY - 10, 200, 20,
                    new StringTextComponent("Password"));
            this.passwordField.setMaxLength(32);
            this.addButton(this.passwordField);

            this.loginButton = this.addButton(new Button(centerX - 100, centerY + 30, 95, 20,
                    new StringTextComponent(Messages.LOGIN_BUTTON), (button) -> {
                this.login();
            }));

            this.cancelButton = this.addButton(new Button(centerX + 5, centerY + 30, 95, 20,
                    new StringTextComponent(Messages.CANCEL_BUTTON), (button) -> {
                this.onClose();
            }));

            this.setInitialFocus(this.usernameField);
        }

        private void login() {
            if (isLoading) return;

            String username = this.usernameField.getValue().trim();
            String password = this.passwordField.getValue().trim();

            if (username.isEmpty() || password.isEmpty()) {
                this.errorMessage = "Kullan\u0131c\u0131 ad\u0131 ve \u015Fifre bo\u015F olamaz!";
                return;
            }

            this.isLoading = true;
            this.errorMessage = "Do\u011Frulan\u0131yor...";

            DatabaseManager.authenticateUser(username, password).thenAccept(success -> {
                Minecraft.getInstance().execute(() -> {
                    this.isLoading = false;
                    if (success) {
                        isAuthenticated = true;
                        currentUser = username;
                        modActive = true;
                        this.errorMessage = Messages.LOGIN_SUCCESS;
                        LOGGER.info(Messages.WELCOME + currentUser);

                        new Thread(() -> {
                            try {
                                Thread.sleep(1000);
                                Minecraft.getInstance().execute(() -> {
                                    this.onClose();
                                });
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }).start();
                    } else {
                        this.errorMessage = Messages.LOGIN_FAILED;
                        this.passwordField.setValue("");
                    }
                });
            }).exceptionally(throwable -> {
                Minecraft.getInstance().execute(() -> {
                    this.isLoading = false;
                    this.errorMessage = Messages.DB_ERROR;
                    LOGGER.error("Authentication error: " + throwable.getMessage());
                });
                return null;
            });
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            this.renderBackground(matrixStack);

            int centerX = this.width / 2;
            int centerY = this.height / 2;

            drawCenteredString(matrixStack, this.font, "Maden Mod - Giri\u015F", centerX, centerY - 80, 0xFFFFFF);

            drawString(matrixStack, this.font, Messages.USER_LABEL, centerX - 100, centerY - 65, 0xFFFFFF);
            drawString(matrixStack, this.font, Messages.PASSWORD_LABEL, centerX - 100, centerY - 25, 0xFFFFFF);

            if (!this.errorMessage.isEmpty()) {
                int color = this.errorMessage.equals(Messages.LOGIN_SUCCESS) ? 0x00FF00 :
                        this.errorMessage.equals("Do\u011Frulan\u0131yor...") ? 0xFFFF00 : 0xFF0000;
                drawCenteredString(matrixStack, this.font, this.errorMessage, centerX, centerY + 60, color);
            }

            super.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
                this.login();
                return true;
            }
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }
    }

    @EventBusSubscriber(modid = "examplemod", bus = Bus.FORGE, value = Dist.CLIENT)
    public static class KeyInputHandler {
        @SubscribeEvent(priority = EventPriority.NORMAL)
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            Minecraft mc = Minecraft.getInstance();
            if (event.getKey() == GLFW.GLFW_KEY_F6 && event.getAction() == GLFW.GLFW_PRESS) {
                if (!isAuthenticated) {
                    mc.setScreen(new LoginScreen());
                } else {
                    modActive = !modActive;
                    if (modActive) {
                        LOGGER.info(Messages.MOD_STARTED);
                    } else {
                        resetState();
                        LOGGER.info(Messages.MOD_STOPPED);
                    }
                }
            }
        }

        private static void resetState() {
            Minecraft mc = Minecraft.getInstance();
            if (mc.options != null) {
                mc.options.keyAttack.setDown(false);
                mc.options.keyUp.setDown(false);
            }
            isAttacking = false;
            shouldMoveForward = false;
            breakingTarget = null;
            movementTarget = null;
        }
    }


    @EventBusSubscriber(modid = "examplemod", bus = Bus.FORGE, value = Dist.CLIENT)
    public static class BreakerTickHandler {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;

            Minecraft mc = Minecraft.getInstance();
            ClientPlayerEntity player = mc.player;

            if (!modActive || !isAuthenticated || player == null || mc.level == null) {
                if (modActive) KeyInputHandler.resetState();
                return;
            }


            int pickaxeSlot = findPickaxeSlot(player);
            if (pickaxeSlot == -1) {
                LOGGER.info(Messages.NO_PICKAXE);
                return;
            }

            if (player.inventory.selected != pickaxeSlot) {
                player.inventory.selected = pickaxeSlot;
            }

            if (breakingTarget != null) {
                if (isValidTarget(mc, breakingTarget)) {
                    continueBreaking(mc, player, breakingTarget);
                } else {
                    resetBreaking(mc);
                }
                return;
            }

            BlockPos target = findNextTarget(mc, player);
            if (target == null) {
                LOGGER.info(Messages.NO_BLOCKS);
                modActive = false;
                KeyInputHandler.resetState();
                return;
            }

            double distance = player.position().distanceTo(Vector3d.atCenterOf(target));
            if (distance > 4.0) {
                approachTarget(mc, player, target);
            } else {
                startBreaking(mc, player, target);
            }
        }

        private static void resetBreaking(Minecraft mc) {
            mc.options.keyAttack.setDown(false);
            isAttacking = false;
            breakingTarget = null;
        }

        private static boolean isValidTarget(Minecraft mc, BlockPos pos) {
            return mc.level.getBlockState(pos).getBlock().equals(Blocks.MOSSY_COBBLESTONE);
        }

        private static void continueBreaking(Minecraft mc, ClientPlayerEntity player, BlockPos target) {
            Vector3d targetCenter = Vector3d.atCenterOf(target);

            if (isLookingAtTarget(player, targetCenter, 2.0f)) {
                mc.options.keyAttack.setDown(true);
                isAttacking = true;
            } else {
                mc.options.keyAttack.setDown(false);
                isAttacking = false;
                lookAt(player, targetCenter);
            }
        }

        private static void approachTarget(Minecraft mc, ClientPlayerEntity player, BlockPos target) {
            Vector3d targetCenter = Vector3d.atCenterOf(target);

            if (isLookingAtTarget(player, targetCenter, 2.0f)) {
                mc.options.keyUp.setDown(true);
                shouldMoveForward = true;
                movementTarget = target;
            } else {
                mc.options.keyUp.setDown(false);
                shouldMoveForward = false;
                lookAt(player, targetCenter);
            }
        }

        private static void startBreaking(Minecraft mc, ClientPlayerEntity player, BlockPos target) {
            Vector3d targetCenter = Vector3d.atCenterOf(target);

            if (shouldMoveForward) {
                mc.options.keyUp.setDown(false);
                shouldMoveForward = false;
                movementTarget = null;
            }

            if (isLookingAtTarget(player, targetCenter, 2.0f)) {
                breakingTarget = target;
                mc.options.keyAttack.setDown(true);
                isAttacking = true;
            } else {
                lookAt(player, targetCenter);
            }
        }

        private static BlockPos findNextTarget(Minecraft mc, ClientPlayerEntity player) {
            BlockPos nearest = findNearestMossyInLayer(mc, player.position(), currentYLevel);

            if (nearest == null && currentYLevel > Math.min(y1, y2)) {
                currentYLevel--;
                LOGGER.info(Messages.LEVEL_DECREASED + currentYLevel);
                nearest = findNearestMossyInLayer(mc, player.position(), currentYLevel);
            }

            return nearest;
        }

        private static BlockPos findNearestMossyInLayer(Minecraft mc, Vector3d playerPos, int yLevel) {
            int minX = Math.min(x1, x2);
            int maxX = Math.max(x1, x2);
            int minZ = Math.min(z1, z2);
            int maxZ = Math.max(z1, z2);

            BlockPos nearest = null;
            double minDist = Double.MAX_VALUE;

            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, yLevel, z);
                    BlockState state = mc.level.getBlockState(pos);

                    if (state.getBlock().equals(Blocks.MOSSY_COBBLESTONE)) {
                        double distance = playerPos.distanceTo(Vector3d.atCenterOf(pos));
                        if (distance < minDist) {
                            minDist = distance;
                            nearest = pos;
                        }
                    }
                }
            }

            return nearest;
        }

        private static int findPickaxeSlot(ClientPlayerEntity player) {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = player.inventory.getItem(i);
                if (isPickaxe(stack.getItem())) {
                    return i;
                }
            }
            return -1;
        }

        private static boolean isPickaxe(Item item) {
            return item == Items.WOODEN_PICKAXE ||
                    item == Items.STONE_PICKAXE ||
                    item == Items.IRON_PICKAXE ||
                    item == Items.GOLDEN_PICKAXE ||
                    item == Items.DIAMOND_PICKAXE ||
                    item == Items.NETHERITE_PICKAXE;
        }

        private static boolean isLookingAtTarget(ClientPlayerEntity player, Vector3d target, float threshold) {
            Vector3d eyes = player.position().add(0, player.getEyeHeight(), 0);
            double dx = target.x - eyes.x;
            double dy = target.y - eyes.y;
            double dz = target.z - eyes.z;
            double distXZ = Math.sqrt(dx*dx + dz*dz);

            float targetYaw = (float)(Math.toDegrees(Math.atan2(-dx, dz)));
            float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, distXZ)));

            float yawDiff = Math.abs(wrapDegrees(player.yRot - targetYaw));
            float pitchDiff = Math.abs(player.xRot - targetPitch);

            return yawDiff < threshold && pitchDiff < threshold;
        }

        private static void lookAt(ClientPlayerEntity player, Vector3d target) {
            Vector3d eyes = player.position().add(0, player.getEyeHeight(), 0);
            double dx = target.x - eyes.x;
            double dy = target.y - eyes.y;
            double dz = target.z - eyes.z;
            double distXZ = Math.sqrt(dx*dx + dz*dz);

            float targetYaw = (float)(Math.toDegrees(Math.atan2(-dx, dz)));
            float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, distXZ)));

            float maxStep = 16.0f;
            float newYaw = approachAngle(player.yRot, targetYaw, maxStep);
            float newPitch = approachAngle(player.xRot, targetPitch, maxStep);

            player.yRot = newYaw;
            player.xRot = newPitch;
            player.yRotO = newYaw;
            player.xRotO = newPitch;
        }

        private static float approachAngle(float current, float target, float maxStep) {
            float delta = wrapDegrees(target - current);
            if (Math.abs(delta) > maxStep) {
                current += Math.signum(delta) * maxStep;
            } else {
                current = target;
            }
            return wrapDegrees(current);
        }

        private static float wrapDegrees(float degrees) {
            degrees = degrees % 360.0f;
            if (degrees >= 180.0f) degrees -= 360.0f;
            if (degrees < -180.0f) degrees += 360.0f;
            return degrees;
        }
    }

    @EventBusSubscriber(modid = "examplemod", bus = Bus.FORGE, value = Dist.CLIENT)
    public static class OverlayRenderer {
        @SubscribeEvent
        public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
            if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            MatrixStack matrixStack = event.getMatrixStack();
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            String statusText;
            if (!isAuthenticated) {
                statusText = Messages.AUTH_REQUIRED;
            } else {
                statusText = modActive ? Messages.MOD_ACTIVE : Messages.MOD_INACTIVE;
            }

            int textWidth = mc.font.width(statusText);
            int x = screenWidth - textWidth - 10;
            int y = 10;

            int bgColor = 0x80000000;
            mc.gui.fill(matrixStack, x - 5, y - 2, x + textWidth + 5, y + mc.font.lineHeight + 2, bgColor);
            mc.font.draw(matrixStack, statusText, x, y, 0xFFFFFF);

            if (isAuthenticated) {
                String userText = Messages.WELCOME + currentUser;
                int userWidth = mc.font.width(userText);
                int userX = screenWidth - userWidth - 10;
                int userY = y + mc.font.lineHeight + 3;

                mc.gui.fill(matrixStack, userX - 5, userY - 2, userX + userWidth + 5, userY + mc.font.lineHeight + 2, bgColor);
                mc.font.draw(matrixStack, userText, userX, userY, 0x00FF00);

                if (modActive) {
                    String levelText = Messages.Y_LEVEL + currentYLevel;
                    String targetText = breakingTarget != null ?
                            Messages.TARGET_FOUND + breakingTarget.getX() + ", " + breakingTarget.getZ() :
                            Messages.TARGET_SEARCHING;

                    int levelWidth = mc.font.width(levelText);
                    int targetWidth = mc.font.width(targetText);
                    int maxWidth = Math.max(levelWidth, targetWidth);

                    int y3 = userY + mc.font.lineHeight + 5;
                    int x3 = screenWidth - maxWidth - 10;
                    mc.gui.fill(matrixStack, x3 - 5, y3 - 2, x3 + levelWidth + 5, y3 + mc.font.lineHeight + 2, bgColor);
                    mc.font.draw(matrixStack, levelText, x3, y3, 0xFFFFFF);

                    int y4 = y3 + mc.font.lineHeight + 3;
                    int x4 = screenWidth - maxWidth - 10;
                    mc.gui.fill(matrixStack, x4 - 5, y4 - 2, x4 + targetWidth + 5, y4 + mc.font.lineHeight + 2, bgColor);
                    mc.font.draw(matrixStack, targetText, x4, y4, 0xFFFFFF);
                }
            }
        }
    }
}