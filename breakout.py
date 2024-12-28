import pygame
import random

# Inicializa o pygame
pygame.init()

# Configurações da tela
largura_tela = 600
altura_tela = 650
tela = pygame.display.set_mode((largura_tela, altura_tela))
pygame.display.set_caption("Breakout")

# Cores
PRETO = (0, 0, 0)
VERMELHO = (255, 0, 0)
CORES_BLOCOS = [
    (0, 0, 255), (0, 255, 0), (255, 0, 0), (0, 255, 255),
    (255, 0, 255), (255, 255, 0), (225, 225, 225), (204, 204, 204),
    (153, 153, 153), (102, 102, 102), (51, 51, 51)
]

# Configurações do jogo
tamanho_bola = 10
x_bola = 20
y_bola = 200
dx = 2
dy = 2
barra_x = 150
barra_largura = 100
barra_altura = 20
fase = 1
pontos = 0
vivo = False

# Função para inicializar o jogo
def inicio():
    global dx, dy, x_bola, y_bola, vivo, barra_x, blocos, fase
    dx = 2
    dy = 3
    x_bola = 20
    y_bola = 200
    vivo = True
    barra_x = 150
    blocos = []
    for c in range(fase):
        for j in range(5):
            for i in range(8):
                blocos.append(pygame.Rect(i * 50, j * 20, 50, 20))

# Função para mover a barra
def move_barra(tecla):
    global barra_x
    if tecla == pygame.K_LEFT:
        barra_x -= 40
    if tecla == pygame.K_RIGHT:
        barra_x += 40
    if barra_x < 0:
        barra_x = 0
    if barra_x > largura_tela - barra_largura:
        barra_x = largura_tela - barra_largura

# Função para atualizar a tela
def anda():
    global x_bola, y_bola, dx, dy, vivo, pontos, fase
    tela.fill(PRETO)
    pygame.draw.rect(tela, VERMELHO, (barra_x, altura_tela - barra_altura, barra_largura, barra_altura))
    if vivo:
        for bloco in blocos:
            pygame.draw.rect(tela, random.choice(CORES_BLOCOS), bloco)
        for bloco in blocos:
            if bloco.colliderect(pygame.Rect(x_bola, y_bola, tamanho_bola, tamanho_bola)):
                dy *= -1
                blocos.remove(bloco)
                pontos += 1
        x_bola += dx
        y_bola += dy
        if y_bola >= altura_tela - barra_altura - tamanho_bola:
            if barra_x <= x_bola <= barra_x + barra_largura:
                dy *= -1
            else:
                vivo = False
                pontos = 0
        if y_bola <= 0 or y_bola >= altura_tela - tamanho_bola:
            dy *= -1
        if x_bola <= 0 or x_bola >= largura_tela - tamanho_bola:
            dx *= -1
        pygame.draw.circle(tela, (0, 255, 0), (x_bola, y_bola), tamanho_bola)
        if not blocos:
            fase += 1
            inicio()

# Loop principal do jogo
inicio()
clock = pygame.time.Clock()
rodando = True
while rodando:
    for evento in pygame.event.get():
        if evento.type == pygame.QUIT:
            rodando = False
        if evento.type == pygame.KEYDOWN:
            move_barra(evento.key)
    anda()
    pygame.display.flip()
    clock.tick(60)

pygame.quit()
